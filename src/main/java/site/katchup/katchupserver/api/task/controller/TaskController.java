package site.katchup.katchupserver.api.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.card.dto.response.CardListGetResponseDto;
import site.katchup.katchupserver.api.card.service.CardService;

import site.katchup.katchupserver.api.task.dto.request.TaskCreateRequestDto;
import site.katchup.katchupserver.api.task.dto.request.TaskUpdateRequestDto;
import site.katchup.katchupserver.api.task.dto.response.TaskGetResponseDto;
import site.katchup.katchupserver.api.task.service.TaskService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor(access = PRIVATE)
@RequestMapping("/api/v1/tasks")
@Tag(name = "[Task] 업무 관련 API (V1)")
public class TaskController {

    private final CardService cardService;
    private final TaskService taskService;

    @Operation(summary = "카테고리 내의 업무 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 내의 업무 조회 성공"),
            @ApiResponse(responseCode = "400", description = "카테고리 내의 업무 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<TaskGetResponseDto>> getByCategoryId(@PathVariable final Long categoryId) {
        return ApiResponseDto.success(taskService.getAllByCategory(categoryId));
    }

    @Operation(summary = "업무 업데이트 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업무 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "업무 업데이트 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PatchMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateTaskName(@PathVariable final Long taskId,
                                             @RequestBody @Valid final TaskUpdateRequestDto requestDto) {
        taskService.updateTaskName(taskId, requestDto);
        return ApiResponseDto.success();
    }

    @Operation(summary = "업무 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "업무 생성 성공"),
            @ApiResponse(responseCode = "400", description = "업무 생성 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto createTaskName(@RequestBody @Valid final TaskCreateRequestDto requestDto, HttpServletResponse response) {
        Long taskId = taskService.createTaskName(requestDto);
        response.addHeader("Location", String.valueOf(taskId));
        return ApiResponseDto.success();
    }

    @Operation(summary = "업무 내의 업무 카드 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업무 내의 업무 카드 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "업무 내의 업무 카드 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/{taskId}/cards")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CardListGetResponseDto>> getCardList(@PathVariable Long taskId) {
        return ApiResponseDto.success(cardService.getCardList(taskId));
    }

    @Operation(summary = "업무 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업무 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "업무 삭제 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ApiResponseDto.success();
    }
}
