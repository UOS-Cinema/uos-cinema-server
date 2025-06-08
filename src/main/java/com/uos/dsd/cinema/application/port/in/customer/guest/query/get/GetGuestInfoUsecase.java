package com.uos.dsd.cinema.application.port.in.customer.guest.query.get;

public interface GetGuestInfoUsecase {
    
    GuestInfoResponse getGuestInfo(GetGuestInfoQuery query);
} 
