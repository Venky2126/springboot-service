common-encryption-service:

required service's

1.registration-service

2.amazon-service



1.FeignClient String  API: http://localhost:8004/registration-service/api/customer/add

2.FeignClient Header & Request Body API: http://localhost:2000/amazon-service/api/v1/add

1.String

curl --location 'http://localhost:8003/common-encryption-service/api/customer/add' \
--header 'Content-Type: application/json' \
--data '{
    "encrypted_payload": "eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.Uh3Uke7ISiVQ5hH9TjjRpprdnkHljaue57Kb5jj9MSOP2PKUUPUolgJEYNPxDVoLSP6YyufJUM4-zaKcjqJPKjABK2RbDbDGQ-f2d-sxtSoAyvqWf1yfD3dMhXuP-Mb4QWjxXXK0UsJ0PAxguP96WdgivaaqK4NLzhZz_RCgYcz4FkXL45tA08t603hQ5JyzdIyg0Y5wmh4LfzcB4bMN0acfHj9sv_4o6nwKRqta11Ake_wov2MOuwoxfiBx63inixFXl4H4cTe6z3Ko4JW-KwMEuUUH4qjRUIgETiuSWKJlHgr8LcOc7l-cYB7JoiVUlZ_kvy0_yYe5QYI61TcRRw.z8slqWRvwyBWF3gqvWigyg.SsoXhlsblTzV0RcLpLvlktMZjBMc5ADlqZ6rGSG3BLo5VlNjwgS5t4nwx4nTPO-dKjNeoOoXur6UU8z7SkdRsuD7SgV6N1Jo5N37i0HVCvOAY8oGJ6JrCvgdf9t0_wMvqkDdN0RMjv46vriiFs_CKAht4mbHci_u_ch1bH0W5RqyqQklyUx7nJWmhk4XqJInAK5o-uVffo89-IYwEHTRxquXfJJV1cHbSnf0nlxM6n8_tayC4Eq_kBQcsItZvVQ4NXTNOB2Vq2vluvmdxAQzdglX1eNAQSSvt5cZkxIiMBM.0NyPpZKQXZRSj5yvqoFOFQ"
}'

2.Request API

curl --location 'http://localhost:8003/common-encryption-service/api/v1/add' \
--header 'channel_identifer: TTD' \
--header 'transaction_date_time: 2024-12-30 12:15:30' \
--header 'transaction_time_zone: IND' \
--header 'country_of_origin: IST' \
--header 'transaction_id: 87654' \
--header 'Content-Type: application/json' \
--data '{"encrypted_payload":"eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.f3zDQlEqSGMyw2Uw3PL1NXsNGBUUaBTvh3ua98r6bSIQ4vyJmVut71MFi1mX4nBk_x2LGwga-iWPpo7XYrGui3rbTPJcxLDR3NQMBxs49FuqSrUuH_V_fCkDMRES0qkl4enxEd-8NW6o0s2vHbaTJSGS5-rGy1MhFvQGZQitFBCoJ0_yEQDA_jPXfECMtrSRNRkk7nDKuk5JeWcSr9XBv5OENOi2XD6ek1bKjqZDrxzak5kf0lQbMNgmmZXu02qdsOUYtpAV_WkLfCZeWRlBdIYbSKDRJ5thx_DwJKQqG8iPWn6y1vnbBESDrHIYm8iplECg5oj-2EOQj2sSY2u2VA.HvPN8G1jll81tv0G1Po6dw.RtQGFnOLtqNhl_NfBwFbLLEhbu4l_gJ2aFK2OnY4aTYT8C4EyMZBrgb4t10JN10zAjcnO_JtFoLMDWP1DZSll8-rtNrcAzJOUa0XRiXQ_gNr4oRJ1tSZiGZDL-ISt8_I3y8rom6C8rfKXbyq7KiX8Fte67DtZYAeJSsAsP2muQhPRkAzwgNPEeuRS2dFF_oOeq7NHmqbiA7P0ULqvkM0b2d0yRLKAi6q8WvgpCsa2u9ukaX--SVLvhtWVhEWshkkTI8pw_DoGJPZ88nUGCIOMkyatox8wM6yGxVrrIOmx64.T4Wnf77jEW3iX5xuEK0nSg"}'


3. API with Header & Header validation

curl --location 'http://localhost:8003/common-encryption-service/api/v1/add-channel' \
--header 'channel_identifer: DVS' \
--header 'transaction_date_time: 2024-12-30 12:15:30' \
--header 'transaction_time_zone: IND' \
--header 'country_of_origin: IST' \
--header 'transaction_id: 87654' \
--header 'Content-Type: application/json' \
--data '{"encrypted_payload":"eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiUlNBLU9BRVAtMjU2In0.f3zDQlEqSGMyw2Uw3PL1NXsNGBUUaBTvh3ua98r6bSIQ4vyJmVut71MFi1mX4nBk_x2LGwga-iWPpo7XYrGui3rbTPJcxLDR3NQMBxs49FuqSrUuH_V_fCkDMRES0qkl4enxEd-8NW6o0s2vHbaTJSGS5-rGy1MhFvQGZQitFBCoJ0_yEQDA_jPXfECMtrSRNRkk7nDKuk5JeWcSr9XBv5OENOi2XD6ek1bKjqZDrxzak5kf0lQbMNgmmZXu02qdsOUYtpAV_WkLfCZeWRlBdIYbSKDRJ5thx_DwJKQqG8iPWn6y1vnbBESDrHIYm8iplECg5oj-2EOQj2sSY2u2VA.HvPN8G1jll81tv0G1Po6dw.RtQGFnOLtqNhl_NfBwFbLLEhbu4l_gJ2aFK2OnY4aTYT8C4EyMZBrgb4t10JN10zAjcnO_JtFoLMDWP1DZSll8-rtNrcAzJOUa0XRiXQ_gNr4oRJ1tSZiGZDL-ISt8_I3y8rom6C8rfKXbyq7KiX8Fte67DtZYAeJSsAsP2muQhPRkAzwgNPEeuRS2dFF_oOeq7NHmqbiA7P0ULqvkM0b2d0yRLKAi6q8WvgpCsa2u9ukaX--SVLvhtWVhEWshkkTI8pw_DoGJPZ88nUGCIOMkyatox8wM6yGxVrrIOmx64.T4Wnf77jEW3iX5xuEK0nSg"}' 
