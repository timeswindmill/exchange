package execution;

import com.lmax.disruptor.EventFactory;
import order.Order;
import order.OrderStatus;
import util.IDGenerator;

import java.io.Serializable;

public class ExecutionReport implements Serializable {


    private final static String EMPTYID = "EMPTY";
    private long execReportID;


    private Order order;
    private ExecutionReportType execTransType;
    private ExecutionReportType execReportType;
    private OrderStatus orderStatus;
    private long executionTime;
    private double orderQty;
    private double leavesQty;
    private double cumQty;
    private double avgPx;
    private double lastShares;
    private double lastPx;


    private ExecutionReport() {
        setExecReportID();
    }


    public static ExecutionReport createExecutionReport(Order order, int fillAmount, double fillPrice, long executeTime) {


        ExecutionReport newReport = new ExecutionReport();
        newReport.setOrder(order);
        newReport.setExecTransType(ExecutionReportType.NEW);
        newReport.setExecReportType(ExecutionReportType.NEW);
        newReport.setOrderStatus(order.getOrderStatus());
        newReport.setExecutionTime(executeTime);
        newReport.setOrderQty(fillAmount);
        newReport.setLeavesQty(order.getOpenQuantity());
        newReport.setCumQty(order.getInitialQuantity() - order.getOpenQuantity());
        newReport.setAvgPx(order.getAvgExecutedPrice());
        newReport.setLastShares(fillAmount);
        newReport.setLastPx(fillPrice);

        return newReport;


    }


    public static ExecutionReport createEmptyReport() {
        ExecutionReport newReport = new ExecutionReport();

        newReport.setOrder(null);
        newReport.setExecTransType(ExecutionReportType.NEW);
        newReport.setExecReportType(ExecutionReportType.NEW);
        newReport.setOrderStatus(OrderStatus.NEW);
        newReport.setExecutionTime(0);
        newReport.setOrderQty(0);
        newReport.setLeavesQty(0);
        newReport.setCumQty(0);
        newReport.setAvgPx(0);
        newReport.setLastShares(0);
        newReport.setLastPx(0);

        return newReport;

    }


    public void resetExecutionReport(Order order, int fillAmount, double fillPrice, long executeTime) {

        setExecReportID();
        setOrder(order);
        setExecTransType(ExecutionReportType.NEW);
        setExecReportType(ExecutionReportType.NEW);
        setOrderStatus(order.getOrderStatus());
        setExecutionTime(executeTime);
        setOrderQty(fillAmount);
        setLeavesQty(order.getOpenQuantity());
        setCumQty(order.getInitialQuantity() - order.getOpenQuantity());
        setAvgPx(order.getAvgExecutedPrice());
        setLastShares(fillAmount);
        setLastPx(fillPrice);

    }


    // for use with disruptor pattern
    public final static EventFactory<ExecutionReport> EVENT_FACTORY = new EventFactory<ExecutionReport>() {
        public ExecutionReport newInstance() {
            return createEmptyReport();
        }
    };

    public long getExecReportID() {
        return execReportID;
    }

    public void setExecReportID() {

        execReportID = IDGenerator.INSTANCE.getNextID();

    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ExecutionReportType getExecTransType() {
        return execTransType;
    }

    public void setExecTransType(ExecutionReportType execTransType) {
        this.execTransType = execTransType;
    }

    public ExecutionReportType getExecReportType() {
        return execReportType;
    }

    public void setExecReportType(ExecutionReportType execReportType) {
        this.execReportType = execReportType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    public double getLeavesQty() {
        return leavesQty;
    }

    public void setLeavesQty(double leavesQty) {
        this.leavesQty = leavesQty;
    }

    public double getCumQty() {
        return cumQty;
    }

    public void setCumQty(double cumQty) {
        this.cumQty = cumQty;
    }

    public double getAvgPx() {
        return avgPx;
    }

    public void setAvgPx(double avgPx) {
        this.avgPx = avgPx;
    }

    public double getLastShares() {
        return lastShares;
    }

    public void setLastShares(double lastShares) {
        this.lastShares = lastShares;
    }

    public double getLastPx() {
        return lastPx;
    }

    public void setLastPx(double lastPx) {
        this.lastPx = lastPx;
    }
}
