package net.luis.utils.util;

import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author Luis-st
 *
 */

public class Result<T> implements Supplier<Either<T, String>> {
	
	private final Either<T, String> either;
	
	private Result(Either<T, String> either) {
		this.either = either;
	}
	
	public static <T> @NotNull Result<T> success(T value) {
		return new Result<>(Either.left(value));
	}
	
	public static <T> @NotNull Result<T> error(String error) {
		return new Result<>(Either.right(error));
	}
	
	@Override
	public @NotNull Either<T, String> get() {
		return this.either;
	}
	
	public boolean isSuccess() {
		return this.either.isLeft();
	}
	
	public @NotNull Optional<T> result() {
		return this.either.left();
	}
	
	public @NotNull T orThrow() {
		return this.orThrow((error) -> {
			
		});
	}
	
	public @NotNull T orThrow(Consumer<String> onError) {
		if (this.either.isLeft()) {
			return this.either.leftOrThrow();
		}
		onError.accept(this.either.rightOrThrow());
		throw new NoSuchElementException("No value available");
	}
	
	public boolean isError() {
		return this.either.isRight();
	}
	
	public @NotNull Optional<String> error() {
		return this.either.right();
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
		return Objects.hash(this.either);
	}
	
}
