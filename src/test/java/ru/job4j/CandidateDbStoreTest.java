package ru.job4j;

import org.junit.Test;
import ru.job4j.model.Candidate;
import ru.job4j.store.CandidateDbStore;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDbStoreTest {
    @Test
    public void whenCreateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(1, "Ivan0", "Ivan0 desc", LocalDateTime.now(), new byte[]{1, 2});
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenFindAllCandidates() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(1, "Ivan1", "Ivan1 desc", LocalDateTime.now(), new byte[]{1, 2});
        Candidate candidate2 = new Candidate(2, "Ivan2", "Ivan2 desc", LocalDateTime.now(), new byte[]{1, 3});
        Candidate candidate3 = new Candidate(3, "Ivan3", "Ivan3 desc", LocalDateTime.now(), new byte[]{1, 4});
        store.add(candidate);
        store.add(candidate2);
        store.add(candidate3);
        List<Candidate> candidatesInDb = store.findAll();
        assertThat(candidatesInDb.contains(candidate3), is(true));
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(1, "Ivan4", "Ivan4 desc", LocalDateTime.now(), new byte[]{1, 2});
        Candidate candidate2 = new Candidate(1, "Ivan5", "Ivan5 desc", LocalDateTime.now(), new byte[]{1, 3});
        store.add(candidate);
        store.update(candidate2);
        Candidate candidateInDb = store.findById(candidate2.getId());
        assertThat(candidateInDb.getName(), is(candidate2.getName()));
    }

    @Test
    public void whenFindByIdCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(1, "Ivan6", "Ivan6 desc", LocalDateTime.now(), new byte[]{1, 2});
        Candidate candidate2 = new Candidate(2, "Ivan7", "Ivan7 desc", LocalDateTime.now(), new byte[]{1, 3});
        store.add(candidate);
        store.add(candidate2);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenDeleteCandidate() {
        CandidateDbStore store = new CandidateDbStore(new Main().loadPool());
        Candidate candidate = new Candidate(1, "Ivan8", "Ivan8 desc", LocalDateTime.now(), new byte[]{1, 2});
        store.add(candidate);
        store.delete(candidate);
        assertThat(store.findAll().contains(candidate), is(false));
    }
}
