package com.example.practiceBatch.taskletsvschunks.chunks;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import com.example.practiceBatch.taskletsvschunks.model.Line;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LineProcessor implements ItemProcessor<Line, Line>, StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.debug("Line Processor initialized.");
	}

	@Override
	public Line process(Line line) throws Exception {
		long age = ChronoUnit.YEARS.between(line.getDob(), LocalDate.now());
		log.debug("Calculated age " + age + " for line " + line.toString());
		line.setAge(age);
		return line;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.debug("Line Processor ended.");
		return ExitStatus.COMPLETED;
	}

}
