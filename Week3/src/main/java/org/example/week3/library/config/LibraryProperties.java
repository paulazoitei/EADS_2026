package org.example.week3.library.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "library")
public class LibraryProperties {

    private Event event = new Event();

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public static class Event {

        private int borrowedBooksThreshold;

        public int getBorrowedBooksThreshold() {
            return borrowedBooksThreshold;
        }

        public void setBorrowedBooksThreshold(
                int borrowedBooksThreshold) {

            this.borrowedBooksThreshold =
                    borrowedBooksThreshold;
        }
    }
}