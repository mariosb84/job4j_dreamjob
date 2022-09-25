package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import ru.job4j.model.Candidate;
import ru.job4j.store.CandidateStore;

import java.util.Collection;

@ThreadSafe
@Service
public class CandidateService {

        private final CandidateStore store;

        public CandidateService(CandidateStore store) {
            this.store = store;
        }

        public Collection<Candidate> findAll() {
            return store.findAll();
        }

        public void add(Candidate candidate) {
            store.add(candidate);
        }

        public Candidate findById(int id) {
            return store.findById(id);
        }

        public void update(Candidate candidate) {
            store.update(candidate);
        }
}
