package com.simulator.util;

import java.util.Random;

public class Utils {
	public static Integer getRandom(Integer min, Integer max) {
		return (new Random().nextInt((max - min)+1) + min);
	}
}
