package com.psychology.product.util;

public final class JsonViews {
    public interface UUID {
    }

    public interface FirstName {
    }

    public interface LastName {
    }

    public interface Email {
    }

    public interface Phone {
    }

    public interface Diagnostic {
    }

    public interface Question {
    }

    public interface Answer {
    }

    public interface DiagnosticResult {
    }

    public interface UserView extends UUID, FirstName, LastName, Email, Phone, Diagnostic, Question, Answer, DiagnosticResult {

    }
}
