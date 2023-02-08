package com.ndptest.tool;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static com.ndptest.tool.apiMethod.GET.threadPoolTest.idList;

@RequiredArgsConstructor
@Service
public class ResultService {
    private final ResultRepository resultRepository;

    public List<Result> getList() {
        return this.resultRepository.findAll();
    }

    public void truncateResultTable() {
        resultRepository.truncateResultTable();
    }
}
