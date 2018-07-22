package com.sawan.model

import org.springframework.data.mongodb.core.mapping.Document


@Document(collection="Sawan")
data class ModelMp3(
        var name:String? = null,
        var avatar:String? = null
       )


