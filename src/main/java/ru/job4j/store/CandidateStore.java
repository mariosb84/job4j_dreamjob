package ru.job4j.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;

import ru.job4j.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {

    private final AtomicInteger id = new AtomicInteger();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        add(new Candidate(1, "Ivan", "Junior Java Developer", LocalDateTime.now()));
        add(new Candidate(2, "Pavel", "Middle  Java Developer", LocalDateTime.now()));
        add(new Candidate(3, "Dmitriy", "Senior  Java Developer", LocalDateTime.now()));
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
