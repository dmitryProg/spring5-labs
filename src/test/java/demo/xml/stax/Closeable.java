package demo.xml.stax;

import lab.common.function.ExceptionalConsumer;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Closeable extends AutoCloseable {

    @SneakyThrows
    static <T extends AutoCloseable, R> R map(T t, Function<T, R> mapper) {
        try (t) {
            return mapper.apply(t);
        }
    }

    @SneakyThrows
    static <T extends AutoCloseable, R> R map(Supplier<T> tSupplier, Function<T, R> mapper) {
        return map(tSupplier.get(), mapper);
    }

    static <T, R, E extends Exception> R map(@NotNull T t, Function<T, R> mapper, ExceptionalConsumer<T, E> close) {
        try {
            return mapper.apply(t);
        } finally {
            close.accept(t);
        }
    }

    static <T, R, E extends Exception> R map(Supplier<T> tSupplier, Function<T, R> mapper, ExceptionalConsumer<T, E> close) {
        return map(tSupplier.get(), mapper, close);
    }

    @SneakyThrows
    static <T extends AutoCloseable> void with(T t, Consumer<T> consumer) {
        try (t) {
            consumer.accept(t);
        }
    }

    @SneakyThrows
    static <T extends AutoCloseable> void with(Supplier<T> tSupplier, Consumer<T> consumer) {
        with(tSupplier.get(), consumer);
    }

    static <T, E extends Exception> void with(T t, Consumer<T> consumer, ExceptionalConsumer<T, E> close) {
        try {
            consumer.accept(t);
        } finally {
            close.accept(t);
        }
    }

    @SneakyThrows
    static <T, E extends Exception> void with(Supplier<T> tSupplier, Consumer<T> consumer, ExceptionalConsumer<T, E> close) {
        with(tSupplier.get(), consumer, close);
    }
}
