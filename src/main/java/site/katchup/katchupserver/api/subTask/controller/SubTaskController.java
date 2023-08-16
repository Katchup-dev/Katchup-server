package site.katchup.katchupserver.api.subTask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.subTask.dto.request.SubTaskCreateRequestDto;
import site.katchup.katchupserver.api.subTask.dto.response.SubTaskGetResponseDto;
import site.katchup.katchupserver.api.subTask.service.SubTaskService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subTasks")
@RequiredArgsConstructor
@Tag(name = "[SubTask] 세부 업무 관련 API (V1)")
public class SubTaskController {
    private final SubTaskService subTaskService;

    @Operation(summary = "세부 업무 목록 조회 API")
    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "세부 업무 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "세부 업무 목록 조회 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ApiResponseDto<List<SubTaskGetResponseDto>> getAllSubTask(Principal principal, @PathVariable Long taskId) {
        return ApiResponseDto.success(subTaskService.getAllSubTask(taskId));
    }

    @Operation(summary = "세부 업무 생성 API")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "201", description = "세부 업무 생성 성공"),
                    @ApiResponse(responseCode = "400", description = "세부 업무 생성 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ApiResponseDto createSubTask(@Valid @RequestBody SubTaskCreateRequestDto requestDto) {
        subTaskService.createSubTask(requestDto);
        return ApiResponseDto.success();
    }
}