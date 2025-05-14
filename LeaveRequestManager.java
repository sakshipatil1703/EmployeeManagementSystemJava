import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestManager {
    private static final String FILE_PATH = "leave_requests.txt";

    public static List<LeaveRequest> loadLeaveRequests() {
        List<LeaveRequest> requests = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return requests;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                LeaveRequest lr = LeaveRequest.fromString(line);
                if (lr != null) {
                    requests.add(lr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public static void saveLeaveRequests(List<LeaveRequest> requests) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (LeaveRequest lr : requests) {
                bw.write(lr.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
