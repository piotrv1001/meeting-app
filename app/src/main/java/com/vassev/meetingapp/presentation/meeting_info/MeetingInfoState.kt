package com.vassev.meetingapp.presentation.meeting_info

import com.vassev.meetingapp.data.remote.dto.MeetingDTO
import com.vassev.meetingapp.domain.responses.GenerateMeetingTimeResponse

data class MeetingInfoState(
    val isLoadingMeetingInfo: Boolean = false,
    val meetingDTO: MeetingDTO? = null,
    val lastMeetingMessage: String = ""
)
