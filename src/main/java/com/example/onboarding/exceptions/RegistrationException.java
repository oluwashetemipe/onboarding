package com.example.onboarding.exceptions;

public class RegistrationException extends Exception {

        public RegistrationException() {
        }

        public RegistrationException(String message) {
            super(message);
        }

        public RegistrationException(String message, Throwable cause) {
            super(message, cause);
        }

        public RegistrationException(Throwable cause) {
            super(cause);
        }
}
