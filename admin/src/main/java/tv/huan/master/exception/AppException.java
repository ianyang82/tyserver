package tv.huan.master.exception;

public class AppException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4294959653049942990L;
	private int code=0;
	private String info;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public AppException(int code, String info) {
		super();
		this.code = code;
		this.info = info;
	}
	public AppException() {
		super();
	}
	public AppException(int code) {
		super();
		this.code = code;
	}
	
}
