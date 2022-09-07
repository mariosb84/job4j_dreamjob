package ru.job4j.store;

import ru.job4j.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan", "Junior Java Developer", LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Pavel", "Middle  Java Developer", LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Dmitriy", "Senior  Java Developer", LocalDateTime.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
