package ghojeong.log.repository;

import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.stereotype.Component;

@Component
public class ActuatorHttpTraceRepository extends InMemoryHttpExchangeRepository {}
