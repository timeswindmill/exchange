package book.out;

import com.lmax.disruptor.EventHandler;
import db.ConnectionPool;
import execution.ExecutionReport;
import instruments.Instrument;
import instruments.SymbolType;
import machine.Machine;
import util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportDBHandler implements EventHandler<ExecutionReport> {

    private final ConnectionPool connectionPool;
    //  private final Instrument instrument;
    private final String instrumentName;
    private long systemStartTime = System.currentTimeMillis();


    private ReportDBHandler(Instrument instrument) {
        // this.instrument = instrument;
        instrumentName = instrument.getSymbol(SymbolType.RIC);
        connectionPool = createConnectionPool();

    }

    private ReportDBHandler() {
        //  instrument = null;
        instrumentName = null;
        connectionPool = null;

    }

    public ConnectionPool createConnectionPool() {

        ConnectionPool.INSTANCE.setup(Machine.INSTANCE.getDbConfig());
        return ConnectionPool.INSTANCE;

    }


    @Override
    public void onEvent(final ExecutionReport report, final long sequence, final boolean endOfBatch) throws Exception {

        try {
            writeToDB(report);
        } catch (Exception e) {
            System.out.println(" DB Exception " + e);
        }

    }


    private void writeToDB(ExecutionReport report) throws SQLException {


        Connection conn = connectionPool.getConnection();
        long time = System.currentTimeMillis() - systemStartTime;
        PreparedStatement preparedStatement = conn.prepareStatement("insert into executionSummary values (?, ?, ?, ?)");
        preparedStatement.setLong(1, time);
        preparedStatement.setString(2, instrumentName);
        preparedStatement.setDouble(3, report.getLastPx());
        preparedStatement.setInt(4, 0);
        preparedStatement.executeUpdate();

        preparedStatement.close();


//       String insertStatement = "INSERT INTO executionReports('client','ClientOrderID','ExecutionID','ExecutionTime','Quantity','Price') VALUES (?,?,?,?,?)";
        String insertStatement = "INSERT INTO executionReports VALUES (?,?,?,?,?,?)";

        PreparedStatement reportStatement = conn.prepareStatement(insertStatement);
        reportStatement.setString(1, report.getOrder().getClient().getClientName());
        reportStatement.setString(2, report.getOrder().getClientOrderID());
        reportStatement.setLong(3, report.getExecReportID());
        reportStatement.setLong(4, report.getExecutionTime());
        reportStatement.setDouble(5, report.getLastShares());
        reportStatement.setDouble(6, report.getLastPx());
        reportStatement.executeUpdate();
        conn.close();
        reportStatement.close();

    }

    public static ReportDBHandler createReportDBHandler(Instrument instrument) {
        ReportDBHandler reportDBHandler = new ReportDBHandler(instrument);
        return reportDBHandler;
    }

    public static ReportDBHandler createMockReportDBHandler() {
        ReportDBHandler reportDBHandler = new ReportDBHandler() {
            private void writeToDB(ExecutionReport report) throws SQLException {
                Log.INSTANCE.logInfo("Report  : " + report.toString());
                Log.INSTANCE.logInfo("Sent to Mock DB  ");
            }


        };
        return reportDBHandler;
    }

}
