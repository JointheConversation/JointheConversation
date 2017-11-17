package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.Thread;
import live.jointheconversation.demo.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {
    private final ThreadRepository threadDao;
    private List<Thread> threads;

    @Autowired
    public ThreadService(ThreadRepository threadDao) {
        this.threadDao=threadDao;
    }

    public Thread save(Thread thread) {
        return threadDao.save(thread);
    }
    public Iterable<Thread> findAll() {
        return threadDao.findAll();
    }

    public Thread findById(long id) {
        return threadDao.findOne(id);
    }

    public void update(Thread thread){
        threads.set((int) thread.getId()-1, thread);
    }
    public void delete(long id) {
        threadDao.delete(id);
    }
}
