package geym.conc.ch5.disruptor;

import com.java.thread.disruptor.EventHandler;

public class PCDataHandler implements EventHandler<PCData>
{
    public void onEvent(PCData event, long sequence, boolean endOfBatch)
    {
        System.out.println(Thread.currentThread().getId()+":Event: **" + event.get()*event.get()+"**");
    }
}