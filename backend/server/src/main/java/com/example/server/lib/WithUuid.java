package com.example.server.lib;

import java.util.UUID;

public record WithUuid<T>(T value, UUID id){}