# from helpers.fb.scraper import get_img_url_for_id, get_posts_for_uid, get_images_for_uid, get_personal_infos_for_id, \
#     get_likes_for_uid, get_family_for_uid, get_locations_for_uid
# from helpers.matching.similarity import get_most_similar_star
# from helpers.text_analysis.extract import extract_keywords
#
# access_token = "EAACEdEose0cBANNZCrj3vQaxwsfdPbINziZBU7Phbd8pvX4GKkMZB8HvSlh8bcb7BZAn0TGgcHlbZBSi0I0tny6IzmWkP5mnZAcRO0ZBiZBSe3a3S2fOnK9ZBhhTpdZCZAPXpczCibKhNs8YJPPOPtUZAHsZBxLCR1klrJqTJMWoZA8OLo6JpTJcq77B4yMUxrzecZBAynRJAUHwpliGwZDZD"
#
# target = "me"
# # #
# # posts = get_posts_for_uid(target, access_token=access_token)
# # #images = get_images_for_uid(target, access_token=access_token)
# # #infos = get_personal_infos_for_id(target, access_token=access_token)
# #
# # print(posts)
# #
# # keywords = extract_keywords(" ".join(posts))
# #
# # print(keywords)
#
# print(get_most_similar_star(access_token))

# likes = get_likes_for_uid(target, access_token=access_token)
# #locs = get_locations_for_uid(target, access_token=access_token)
#
# print(posts)
# print(images)
# print(infos)

#
# import json
# import requests
#
#
# def get_auth_code():
#
#     data = "grant_type=client_credentials&scope=hybris.tenant=fcbh01 hybris.profile_view hybris.profile_manage&client_id=L4zUaTke5YVZUhVKMifmILfe5jxJDcFG&client_secret=OtKPGEOhBJYg3jV8"
#     url = 'https://api.us.yaas.io/hybris/oauth2/v1/token'
#     headers = {'Content-Type': 'application/x-www-form-urlencoded', "Origin": "https://builder.yaas.io", "Accept-Encoding": "gzip, deflate, br", "Accept": "application/json, text/plain, */*", "Connection": "keep-alive", "priority": "10"}
#
#     res = requests.post(url, data=data, headers=headers)
#
#     auth_json = json.loads(res.text)
#
#     auth_code = auth_json["access_token"]
#     print(auth_code)
#
#     return auth_code
#
#
# profile_id = "488de6db-f6e1-48c4-8949-838684ab1d22"
# tenant = "fcbh01"
#
#
# def get_user_profile(tenant, profile_id, auth_code):
#
#     url = "https://api.beta.yaas.io/hybris/profile/v1/{}/profiles/{}".format(tenant, profile_id)
#     headers = {'Authorization' : "Bearer {}".format(auth_code), "Content-Type":"application/json"}
#
#     res = requests.get(url, headers=headers)
#     profile_dict = json.loads(res.text)
#
#     print(profile_dict)
#
#     return profile_dict
#
#
# profile_dict= {"extensions" : {"hero" : {"email" : "test2"}}}
# update_prof = json.dumps(profile_dict)
#
# def update_user_profile(update_dict, tenant, profile_id, auth_code):
#
#
#     url = "https://api.beta.yaas.io/hybris/profile/v1/{}/profiles/{}".format(tenant, profile_id)
#     headers = {'Authorization' : "Bearer {}".format(auth_code),  "hybris-consent-reference" : profile_id, "Content-Type":"application/json"}
#     res2 = requests.patch(url, data=update_dict, headers=headers)
#
#     res2.status_code
#
#
#
# def get_identity_auth_code():
#
#     data = "grant_type=client_credentials&scope=hybris.tenant=fcbh01 hybris.profile_identities_view hybris.profile_identities_manage&client_id=zXlKXcRcQizQrclRpMdQctPaIP0ZRy80&client_secret=B6pPQHl1jK6mqmUD"
#     url = 'https://api.us.yaas.io/hybris/oauth2/v1/token'
#     headers = {'Content-Type': 'application/x-www-form-urlencoded', "Connection": "keep-alive", "priority": "10"}
#
#     res = requests.post(url, data=data, headers=headers)
#
#     auth_json = json.loads(res.text)
#
#     auth_code = auth_json["access_token"]
#     print(auth_code)
#
#     return auth_code
#
# email = "xd@xp.de"
#
# def add_email_to_profile(profile_id, email, auth_code, tenant = "fcbh01"):
#
#     url = "https://api.beta.yaas.io/hybris/profile-identity-service/v1/{}/profileReferences/{}".format(tenant, profile_id)
#     headers = {'Authorization' : "Bearer {}".format(auth_code),  "Content-Type":"application/json"}
#     params = {"identityKey" : email, "identityType": "email" , "identityOrigin":"web" }
#
#     res3 = requests.put(url, params=params, headers=headers)
#
#     res3.status_code
#
# def get_profile_for_email(email, auth_code, tenant = "fcbh01"):
#
#     url = "https://api.beta.yaas.io/hybris/profile-identity-service/v1/{}/identities".format(tenant)
#     headers = {'Authorization' : "Bearer {}".format(auth_code),  "Content-Type":"application/json"}
#     params = {"identityKey" : email, "identityType": "email" , "identityOrigin":"web" }
#
#     res4 = requests.get(url, params=params, headers=headers)
#     res_dict = json.loads(res4.text)
#
#     res_profile = res_dict["profiles"][0]
#
#     profile_id = res_profile['profileId']
#
#     return profile_id
#
#
#
#

# from helpers.fb.scraper import get_img_url_for_id, get_profile_pic
#
from helpers.fb.scraper import get_fb_email

access_token = "EAACEdEose0cBABe1CIhdYpLe0CXifP5UADbiwINbYcY1yuuZB5G8J5pmoAD9VArHJDRJjbztoUSEZCmSyiLmaHYwhDnePbiyniXjsnG2tk2kVYYkOZCfJO9A7JlEcnUwnZCmoKRTNAIC5KchR21jz8ZANVHZCNmpjjUVJfoeHpZCHTZB523YdbaeoYZBO8RZBTUxaSjl5ZBA3XyygZDZD"

target = "me"


xd = get_fb_email(id=target, access_token=access_token)

print(xd)

#
# star_faces = ("257c3673-a2e9-4661-8ee8-7426e9460372", "ac24ee8f-3272-4356-a8d5-78a0e73d2422")
# me_face = "2ec5500d-0ef3-4bf2-a3c7-cd88cf6e638a"
#
# import cognitive_face as CF
#
# KEY = '881e3da51c4f4304b7fca6e243e3aed4'  # Replace with a valid Subscription Key here.
# CF.Key.set(KEY)
#
# BASE_URL = 'https://westcentralus.api.cognitive.microsoft.com/face/v1.0/'  # Replace with your regional Base URL
# CF.BaseUrl.set(BASE_URL)
#
# result = CF.face.find_similars(face_id=me_face, face_ids=star_faces, mode="matchFace")
# print(result)


