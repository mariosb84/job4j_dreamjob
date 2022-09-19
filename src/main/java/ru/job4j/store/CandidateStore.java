package ru.job4j.store;

import ru.job4j.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();

    private final AtomicInteger id = new AtomicInteger();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        add(new Candidate(1, "Ivan", "Junior Java Developer", LocalDateTime.now()));
        add(new Candidate(2, "Pavel", "Middle  Java Developer", LocalDateTime.now()));
        add(new Candidate(3, "Dmitriy", "Senior  Java Developer", LocalDateTime.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(id.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
