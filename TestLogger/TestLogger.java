import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class TestLogger {
    
    public static void main(String[] args) {
        String a = "A device broadly refers to any physical or virtual tool designed to perform specific functions or tasks. In today's interconnected world, devices range from simple handheld gadgets to complex machines integral to modern infrastructure and communication. They serve various purposes across industries and daily life, shaping how we interact with technology and each other.";
        String b = "Devices often encompass hardware components, software systems, and sometimes firmware that enable them to function effectively. These components work together to facilitate tasks ranging from personal productivity to industrial automation. Common examples include smartphones, tablets, laptops, and desktop computers, which are essential for communication, entertainment, and work.";
        String c = "Beyond personal electronics, devices include industrial machinery, medical equipment, and scientific instruments. These devices are engineered for specific applications, such as manufacturing processes, healthcare diagnostics, or research experiments. They often integrate advanced sensors, actuators, and computing capabilities to achieve precise and reliable performance.";
        String d = "Security and connectivity are critical aspects of modern devices. With the proliferation of network-connected devices, cybersecurity measures are essential to protect sensitive data and ensure operational integrity. Additionally, seamless connectivity via wired or wireless networks enables real-time data exchange and remote management, enhancing efficiency and convenience.";

        Random rand = new Random();

        for (int i = 1; i <= 10000000; i++) {
            // Simulate random selection of strings a, b, c, d
            String selectedString;
            switch (rand.nextInt(4)) {
                case 0:
                    selectedString = a;
                    break;
                case 1:
                    selectedString = b;
                    break;
                case 2:
                    selectedString = c;
                    break;
                case 3:
                    selectedString = d;
                    break;
                default:
                    selectedString = a; // Default to a
            }
            
            // Simulate random selection of log type
            String logType;
            switch (rand.nextInt(2)) {
                case 0:
                    logType = "INFO";
                    break;
                case 1:
                    logType = "ERROR";
                    break;
                default:
                    logType = "INFO"; // Default to INFO
            }

            // Write log
            writeLog(logType, "API call with selected string: " + selectedString);

            // Introduce a 20ms delay
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // Log the exception
                writeLog("ERROR", "InterruptedException occurred: " + e.getMessage());
            }

            // Print progress every 1,000,000 iterations
            // if (i % 1000000 == 0) {
            //     System.out.println("Progress: " + i + " iterations completed.");
            // }
        }
    }

    // Example method for writing log to a server (replace with actual API call logic)
    private static void writeLog(String type, String message) {
        try {
            URL url = new URL("http://localhost:8080/logger/writelog");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Create JSON payload
            String jsonPayload = "{\"type\":\"" + type + "\",\"logMessage\":\"" + message + "\"}";

            // Write request body
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(jsonPayload);
            writer.flush();

            // Read response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Log entry created successfully: " + message);
            } else {
                System.out.println("Failed to create log entry. Response code: " + responseCode);
                // Log the error
                logError("ERROR", "Failed to create log entry. Response code: " + responseCode);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Log the exception
            logError("ERROR", "IOException occurred: " + e.getMessage());
        }
    }

    // Method to log errors
    private static void logError(String type, String message) {
        try {
            URL url = new URL("http://localhost:8080/logger/writelog");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Create JSON payload
            String jsonPayload = "{\"type\":\"" + type + "\",\"logMessage\":\"" + message + "\"}";

            // Write request body
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(jsonPayload);
            writer.flush();

            // Read response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Error log entry created successfully: " + message);
            } else {
                System.out.println("Failed to create error log entry. Response code: " + responseCode);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
