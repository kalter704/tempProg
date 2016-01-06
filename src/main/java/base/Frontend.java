package base;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Frontend {
    void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException;
    void doPost(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws ServletException, IOException;
}
