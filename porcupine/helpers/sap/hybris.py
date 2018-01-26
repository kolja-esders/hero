import json
import requests


def get_auth_code(tenant="fcbh01"):

    data = "grant_type=client_credentials&scope=hybris.tenant={} hybris.profile_view hybris.profile_manage&client_id=L4zUaTke5YVZUhVKMifmILfe5jxJDcFG&client_secret=OtKPGEOhBJYg3jV8".format(tenant)
    url = 'https://api.us.yaas.io/hybris/oauth2/v1/token'
    headers = {'Content-Type': 'application/x-www-form-urlencoded', "Origin": "https://builder.yaas.io",
               "Accept-Encoding": "gzip, deflate, br", "Accept": "application/json, text/plain, */*",
               "Connection": "keep-alive", "priority": "10"}

    res = requests.post(url, data=data, headers=headers)

    auth_json = json.loads(res.text)

    auth_code = auth_json["access_token"]
    #print(auth_code)

    return auth_code


def get_user_profile(profile_id, auth_code, tenant="fcbh01"):
    url = "https://api.beta.yaas.io/hybris/profile/v1/{}/profiles/{}".format(tenant, profile_id)
    headers = {'Authorization': "Bearer {}".format(auth_code), "Content-Type": "application/json"}

    res = requests.get(url, headers=headers)
    profile_dict = json.loads(res.text)

    print(profile_dict)

    return profile_dict



def update_user_profile(update_dict, profile_id, auth_code, tenant="fcbh01"):
    url = "https://api.beta.yaas.io/hybris/profile/v1/{}/profiles/{}".format(tenant, profile_id)
    headers = {'Authorization': "Bearer {}".format(auth_code), "hybris-consent-reference": profile_id,
               "Content-Type": "application/json"}
    res2 = requests.patch(url, data=json.dumps(update_dict), headers=headers)

    # print(res2.text)


def get_identity_auth_code(tenant="fcbh01"):
    data = "grant_type=client_credentials&scope=hybris.tenant={} hybris.profile_identities_view hybris.profile_identities_manage&client_id=zXlKXcRcQizQrclRpMdQctPaIP0ZRy80&client_secret=B6pPQHl1jK6mqmUD".format(tenant)
    url = 'https://api.us.yaas.io/hybris/oauth2/v1/token'
    headers = {'Content-Type': 'application/x-www-form-urlencoded', "Connection": "keep-alive", "priority": "10"}

    res = requests.post(url, data=data, headers=headers)

    auth_json = json.loads(res.text)

    auth_code = auth_json["access_token"]
    #print(auth_code)

    return auth_code



def add_email_to_profile(profile_id, email, auth_code, tenant="fcbh01"):
    url = "https://api.beta.yaas.io/hybris/profile-identity-service/v1/{}/profileReferences/{}".format(tenant,
                                                                                                       profile_id)
    headers = {'Authorization': "Bearer {}".format(auth_code), "Content-Type": "application/json"}
    params = {"identityKey": email, "identityType": "email", "identityOrigin": "web"}

    res3 = requests.put(url, params=params, headers=headers)

    res3.status_code



def get_profile_for_email(email, auth_code, tenant="fcbh01"):
    url = "https://api.beta.yaas.io/hybris/profile-identity-service/v1/{}/identities".format(tenant)
    headers = {'Authorization': "Bearer {}".format(auth_code), "Content-Type": "application/json"}
    params = {"identityKey": email, "identityType": "email", "identityOrigin": "web"}

    res4 = requests.get(url, params=params, headers=headers)
    res_dict = json.loads(res4.text)

    try:
        res_profile = res_dict["profiles"][0]
        profile_id = res_profile['profileId']
    except:
        profile_id = None

    return profile_id


def create_new_profile(auth_code, tenant="fcbh01"):

    url = "https://api.yaas.io/hybris/profile-consent/v1/{}/consentReferences".format(tenant)
    headers = {'Authorization': "Bearer {}".format(auth_code)}

    res6 = requests.post(url, headers=headers)
    res_dict = json.loads(res6.text)

    return res_dict['id']




