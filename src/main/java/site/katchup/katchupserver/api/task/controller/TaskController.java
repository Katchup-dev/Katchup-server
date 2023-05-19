package site.katchup.katchupserver.api.task.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.task.dto.TaskCreateRequestDto;
import site.katchup.katchupserver.api.task.dto.TaskResponseDto;
import site.katchup.katchupserver.api.task.service.TaskService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.ErrorStatus;
import site.katchup.katchupserver.common.response.SuccessStatus;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<TaskResponseDto>> getAllTask(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(SuccessStatus.GET_ALL_TASK_SUCCESS, taskService.getAllTask(memberId));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto createTask(@Valid @RequestBody TaskCreateRequestDto requestDto) {
        taskService.createTask(requestDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_TASK_SUCCESS);
    }
}