package pl.panszelescik.basicmachines.forge.api.util;

import net.minecraftforge.common.util.LazyOptional;

import java.util.Objects;
import java.util.function.Supplier;

public class LazyOptionalUtils {

    public static <T> LazyOptional<T> or(LazyOptional<T> lazyOptional, Supplier<? extends LazyOptional<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (lazyOptional.isPresent()) {
            return lazyOptional;
        } else {
            @SuppressWarnings("unchecked")
            LazyOptional<T> r = (LazyOptional<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }
}
