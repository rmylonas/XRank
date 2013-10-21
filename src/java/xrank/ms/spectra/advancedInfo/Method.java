package xrank.ms.spectra.advancedInfo;

import java.io.Serializable;

public class Method implements Serializable {

	protected int methodId;
	protected String methodName;

	public Method() {
	}

	public int getMethodId() {
		return methodId;
	}

	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
