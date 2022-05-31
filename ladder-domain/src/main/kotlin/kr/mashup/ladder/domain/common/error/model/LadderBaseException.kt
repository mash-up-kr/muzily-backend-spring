package kr.mashup.ladder.domain.common.error.model

import kr.mashup.ladder.domain.common.error.ErrorCode

abstract class LadderBaseException(
    override val message: String,
    open val errorCode: ErrorCode,
) : RuntimeException(message)


/**
 * InvalidException
 * 400 BadRequest
 */
data class InvalidRequestException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.INVALID_REQUEST,
) : LadderBaseException(message, errorCode)


/**
 * UnAuthorizedException
 * 401 UnAuthorized
 */
data class UnAuthorizedException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.UNAUTHORIZED,
) : LadderBaseException(message, errorCode)


/**
 * ForbiddenException
 * 403 Forbidden
 */
data class ForbiddenException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.FORBIDDEN,
) : LadderBaseException(message, errorCode)


/**
 * NotFoundException
 * 404 NotFound
 */
data class NotFoundException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.NOT_FOUND,
) : LadderBaseException(message, errorCode)


/**
 * ConflictException
 * 409 Conflict
 */
data class ConflictException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.CONFLICT,
) : LadderBaseException(message, errorCode)


/**
 * UnknownErrorException
 * 500 InternalServer
 */
data class UnknownErrorException(
    override val message: String,
    override val errorCode: ErrorCode = ErrorCode.UNKNOWN_ERROR,
) : LadderBaseException(message, errorCode)
