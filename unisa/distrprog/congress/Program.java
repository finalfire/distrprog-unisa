package unisa.distrprog.congress;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class Program implements Serializable {
    private static final int NUMBER_OF_DAYS = 3;
    private final Vector<Day> days;

    public Program() {
        this.days = new Vector<>();
        for (int i = 0; i < NUMBER_OF_DAYS; i++)
            this.days.add(new Day());
    }

    public String getDay(int day) {
        return this.days.get(day).toString();
    }

    @Override
    public String toString() {
        return this.days.toString();
    }

    class Day implements Serializable {
        private static final int NUMBER_OF_SESSIONS = 12;
        private static final int NUMBER_OF_SPEAKERS = 5;

        private final Vector<ArrayList<String>> sessions;

        public Day() {
            this.sessions = new Vector<>();
            for (int i = 0; i < NUMBER_OF_SESSIONS; i++) {
                this.sessions.add(new ArrayList<String>());
            }
        }

        public boolean addSpeaker(String speaker, int session) {
            if (session >= 0 && session < NUMBER_OF_SESSIONS)
                return false;

            if (this.sessions.get(session).size() == NUMBER_OF_SPEAKERS)
                return false;

            this.sessions.get(session).add(speaker);
            return true;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
