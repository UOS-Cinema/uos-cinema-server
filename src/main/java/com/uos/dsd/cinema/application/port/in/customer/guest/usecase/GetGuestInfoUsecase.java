package com.uos.dsd.cinema.application.port.in.customer.guest.usecase;

import com.uos.dsd.cinema.application.port.in.customer.guest.query.GetGuestInfoQuery;
import com.uos.dsd.cinema.application.port.in.customer.guest.response.GuestInfoResponse;

public interface GetGuestInfoUsecase {
    
    GuestInfoResponse getGuestInfo(GetGuestInfoQuery query);
} 
