package cn.nekocode.baseframework.utils;

public class MyCallback {
	public interface Callback0 {
		void run();
	}

	public interface Callback1<T> {
		void run(T param);
	}

	public interface Callback2<T, E> {
		void run(T param, E param2);
	}

	public interface Callback3<T, E, Q> {
		void run(T param, E param2, Q param3);
	}
}

