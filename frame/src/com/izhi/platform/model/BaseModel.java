package com.izhi.platform.model;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = -3296732779182257743L;

	public abstract String toString();

	public abstract boolean equals(Object o);

	public abstract int hashCode();
}
