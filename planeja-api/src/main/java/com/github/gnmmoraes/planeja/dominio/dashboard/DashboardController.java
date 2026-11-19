package com.github.gnmmoraes.planeja.dominio.dashboard;

import com.github.gnmmoraes.planeja.dominio.dashboard.dto.Dashboard;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public Dashboard obterDashboardMesAtual(){
        return dashboardService.obterDashboardMesAtual();
    }

}
