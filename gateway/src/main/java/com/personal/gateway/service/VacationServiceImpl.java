package com.personal.gateway.service;

import com.personal.gateway.adapter.VacationAdapter;
import com.personal.gateway.service.contract.VacationService;
import com.personal.gateway.service.feign.VacationManagementClient;
import com.personal.model.dto.VacationGatewayRp;
import com.personal.model.dto.VacationResponse;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
public class VacationServiceImpl implements VacationService {

    private final VacationManagementClient vacationManagementClient;
    private final VacationAdapter vacationAdapter;

    @Override
    public List<VacationGatewayRp> findUserVacations(String name) {
        try {
            List<VacationResponse> vacationResponses = vacationManagementClient.findUserVacations(name);
            log.info(format("Retrieve user %s vacations", name));
            return vacationResponses.stream().map(vacationAdapter::fromVacationResponseToVacationGatewayRp).toList();
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw new ResourceAccessException("Vacation Service is down");
        }
    }
}
