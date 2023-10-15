package site.katchup.katchupserver.api.member.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.domain.Withdraw;
import site.katchup.katchupserver.api.member.dto.request.MemberDeleteRequestDto;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.member.repository.WithdrawRepository;
import site.katchup.katchupserver.api.member.service.WithdrawService;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.api.task.domain.Task;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WithdrawServiceImpl implements WithdrawService {

    private final WithdrawRepository withdrawRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void deleteMember(Long memberId, MemberDeleteRequestDto memberDeleteRequestDto) {
        Withdraw withdraw = Withdraw.builder()
                .member(memberRepository.findByIdOrThrow(memberId))
                .reason(memberDeleteRequestDto.getReason())
                .build();
        withdrawRepository.save(withdraw);

        Member member = memberRepository.findByIdOrThrow(memberId);
        member.deleted();

        List<Category> categoryList = categoryRepository.findAllByMemberId(memberId);

        categoryList.forEach(Category::deleted);
        categoryList.forEach(category ->
            category.getTasks().forEach(this::deleteTaskAndSubTaskAndCard));

    }

    private void deleteTaskAndSubTaskAndCard(Task task) {
        task.deleted();
        task.getSubTasks().forEach(SubTask::deleted);
        task.getSubTasks().stream()
                .flatMap(subTask -> subTask.getCards().stream())
                .forEach(Card::deletedCard);
    }
}
