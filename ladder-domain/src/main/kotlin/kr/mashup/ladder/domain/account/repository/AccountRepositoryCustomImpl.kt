package kr.mashup.ladder.domain.account.repository

import com.querydsl.jpa.impl.JPAQueryFactory

class AccountRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : AccountRepositoryCustom
