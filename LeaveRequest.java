import java.time.LocalDate;

public class LeaveRequest {
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private Status status;

    public LeaveRequest(String employeeId, LocalDate startDate, LocalDate endDate, String reason) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = Status.PENDING;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return employeeId + "," + startDate + "," + endDate + "," + reason.replace(",", ";") + "," + status;
    }

    public static LeaveRequest fromString(String line) {
        String[] parts = line.split(",", 5);
        if (parts.length < 5) return null;
        String empId = parts[0];
        LocalDate start = LocalDate.parse(parts[1]);
        LocalDate end = LocalDate.parse(parts[2]);
        String reason = parts[3].replace(";", ",");
        Status status = Status.valueOf(parts[4]);
        LeaveRequest lr = new LeaveRequest(empId, start, end, reason);
        lr.setStatus(status);
        return lr;
    }
}
