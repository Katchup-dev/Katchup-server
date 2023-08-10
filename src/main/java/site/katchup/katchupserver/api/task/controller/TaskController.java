package site.katchup.katchupserver.api.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.task.dto.request.TaskCreateRequestDto;
import site.katchup.katchupserver.api.task.dto.response.TaskGetResponseDto;
import site.katchup.katchupserver.api.task.service.TaskService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "[Task] 소분류 관련 API (V1)")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "업무 카드의 모든 소분류 조회 API")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "소분류 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "소분류 조회 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ApiResponseDto<List<TaskGetResponseDto>> getAllTask(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(taskService.getAllTask(memberId));
    }

    @Operation(summary = "소분류 생성 API")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "201", description = "소분류 생성 성공"),
                    @ApiResponse(responseCode = "400", description = "소분류 생성 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ApiResponseDto createTask(@Valid @RequestBody TaskCreateRequestDto requestDto) {
        taskService.createTask(requestDto);
        return ApiResponseDto.success();
    }
}