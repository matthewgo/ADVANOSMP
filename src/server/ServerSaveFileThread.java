package server;

import indie.FileManager;
import indie.ResponseHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;

public class ServerSaveFileThread extends Thread {
	private final Socket socket;
	PrintWriter out;
	BufferedReader in;

	public ServerSaveFileThread(Socket socket, BufferedReader in)
			throws IOException {
		this.socket = socket;
		out = new PrintWriter(socket.getOutputStream(), true);
		this.in = in;
	}

	@Override
	public void run() {

		try {

			receiveFiles();
			out.println("FINISHED");
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receiveFiles() throws IOException {
		String line;
		StringBuilder sb = new StringBuilder();

		String filename = null;
		Timestamp dateModified;
		while ((line = in.readLine()) != null) {
			if (line.equals("END OF TRANSACTION")) {
				// exits the loop after all files from client has been sent
				break;
			}
			if (ResponseHandler.isStartOfFile(line)
					&& ResponseHandler.isEndOfFile(line)) {
				// means that the file has a one line content
				String[] tokens = line.split("###");
				filename = tokens[0];
				dateModified = new Timestamp(Long.valueOf(tokens[1]));
				System.out.println("Name: " + filename);
				System.out.println("Time: " + dateModified);
				line = ResponseHandler.getFirstLineContent(line);

				sb.append(ResponseHandler.removeEndFileDelimiter(line));
				System.out.println(sb.toString());
				File f = new File(ServerMain.folderPath + filename);
				FileManager.writeToFile(f, sb.toString());
				System.out.println("Finished writing " + f.getAbsolutePath());

				sb = new StringBuilder();
			} else if (ResponseHandler.isStartOfFile(line)) {
				// means that the file has more than one line
				// extracts the file name and time modified
				String[] tokens = line.split("###");
				System.out.println("Name: " + tokens[0]);
				System.out.println("Time: "
						+ new Timestamp(Long.valueOf(tokens[1])));
				filename = tokens[0];
				dateModified = new Timestamp(Long.valueOf(tokens[1]));
				line = ResponseHandler.getFirstLineContent(line);
				sb.append(line);
			} else if (ResponseHandler.isEndOfFile(line)) {
				// if reader sees a end of file delimeter, it proceeds to build
				// the next file
				sb.append(ResponseHandler.removeEndFileDelimiter(line));
				System.out.println(sb.toString());
				File f = new File(ServerMain.folderPath + filename);
				FileManager.writeToFile(f, sb.toString());
				System.out.println("Finished writing " + f.getAbsolutePath());
				sb = new StringBuilder();
			} else
				// middle liners in a file
				sb.append(line);

			if (line != null && !ResponseHandler.isEndOfFile(line)) {
				sb.append("\n");
			}

		}
	}
}