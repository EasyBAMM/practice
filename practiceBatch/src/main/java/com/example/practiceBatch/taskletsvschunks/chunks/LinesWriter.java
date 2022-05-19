package com.example.practiceBatch.taskletsvschunks.chunks;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import com.example.practiceBatch.taskletsvschunks.model.Line;
import com.example.practiceBatch.taskletsvschunks.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinesWriter implements ItemWriter<Line>, StepExecutionListener {

	private FileUtils fu;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		fu = new FileUtils("output.csv");
		log.debug("Line Writer initialized.");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		fu.closeWriter();
		log.debug("Line Writer ended.");
		return ExitStatus.COMPLETED;
	}

	@Override
	public void write(List<? extends Line> lines) throws Exception {
		for (Line line : lines) {
			fu.writeLine(line);
			log.debug("Wrote line " + line.toString());
		}
	}

}
