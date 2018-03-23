package top.coos.proxy;

public class HostMapping {

	private final String host;

	private final String target_host;
	private final int target_port;

	public HostMapping(String host, String target_host, int target_port) {

		this.host = host;
		this.target_host = target_host;
		this.target_port = target_port;
	}

	public String getHost() {

		return host;
	}

	public String getTarget_host() {

		return target_host;
	}

	public int getTarget_port() {

		return target_port;
	}

}
