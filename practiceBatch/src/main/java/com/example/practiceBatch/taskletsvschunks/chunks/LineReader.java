package com.example.practiceBatch.taskletsvschunks.chunks;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.example.practiceBatch.taskletsvschunks.model.Line;
import com.example.practiceBatch.taskletsvschunks.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LineReader implements ItemReader<Line>, StepExecutionListener {

	private FileUtils fu;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		fu = new FileUtils("taskletsvschunks/input/tasklets-vs-chunks.csv");
		log.debug("Line Reader initialized.");
	}

	@Override
	public Line read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		Line line = fu.readLine();
		if (line != null)
			log.debug("Read line: " + line.toString());
		return line;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		fu.closeReader();
		log.debug("Line Reader ended.");
		return ExitStatus.COMPLETED;
	}

}
