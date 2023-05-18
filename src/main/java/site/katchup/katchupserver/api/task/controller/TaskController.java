package site.katchup.katchupserver.api.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.task.dto.TaskResponseDto;
import site.katchup.katchupserver.api.task.service.TaskService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;

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
        Long memberId = Member.getMemberId(principal);

        return ApiResponseDto.success(SuccessStatus.GET_ALL_TASK_SUCCESS, taskService.getAllTask(memberId));
    }
}