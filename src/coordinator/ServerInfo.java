package coordinator;

import java.util.ArrayList;
import java.util.List;

public class ServerInfo {

	private String addressWithPortNumber;
	private String ipAddress;
	private int portNumber;
	private boolean isAlive;
	private final List<String> filenames;

	public ServerInfo(String addressWithPortNumber, String ipAddress,
			int portNumber, boolean isAlive) {
		super();
		this.addressWithPortNumber = addressWithPortNumber;
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
		this.isAlive = isAlive;

		filenames = new ArrayList<>();
	}

	public ServerInfo(String ipAddress, int portNumber, boolean isAlive) {
		super();
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
		this.isAlive = isAlive;

		filenames = new ArrayList<>();
	}

	public String getAddressWithPortNumber() {
		return addressWithPortNumber;
	}

	public void setIpAddressWithPortNumber(String addressWithPortNumber) {
		this.addressWithPortNumber = addressWithPortNumber;
	}

	public List<String> getFilenames() {
		return filenames;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void addFilename(String filename) {
		filenames.add(filename);
	}
}