package net.luis.utils.util;

import net.luis.utils.math.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *
 * @author Luis-st
 *
 */

public class Range {
	
	private final int min;
	private final int max;
	
	private Range(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public static @NotNull Range of(int max) {
		return of(0, max);
	}
	
	public static @NotNull Range of(int min, int max) {
		if (min > max) {
			throw new RuntimeException("The maximum value must be greater than the minimum value");
		} else {
			return new Range(min, max);
		}
	}
	
	public int getMin() {
		return this.min;
	}
	
	public int getMax() {
		return this.max;
	}
	
	public boolean isInRange(int value) {
		return Mth.isInBounds(value, this.min, this.max);
	}
	
	@Override
	public String toString() {
		return ToString.toString(this);
	}
	
	@Override
	public boolean equals(Object object) {
		return Equals.equals(this, object);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.min, this.max);
	}
	
}
