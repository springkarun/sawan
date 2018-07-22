package com.sawan.repository

import com.sawan.model.ModelMp3
import org.springframework.data.mongodb.repository.MongoRepository


interface Mp3Repository : MongoRepository<ModelMp3, Int>