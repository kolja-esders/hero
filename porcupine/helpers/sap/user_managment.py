from helpers.model.user import create_plain_user
from helpers.sap.hybris import get_identity_auth_code, get_profile_for_email, get_auth_code, create_new_profile, \
    add_email_to_profile, update_user_profile, get_user_profile


def get_user_profile_id(email):
    try:
        auth_code = get_identity_auth_code()
        profile_id = get_profile_for_email(email, auth_code)

        if profile_id is not None:
            return profile_id
        else:
            return None

    except:
        return None


def does_user_exist(email):

    profile_id = get_user_profile_id(email)

    if profile_id is not None:
        return True
    else:
        return False


def create_user(email):

    user = create_plain_user()
    user["email"] = email

    auth_code = get_identity_auth_code()
    profile_id = create_new_profile(auth_code)
    user["profile_id"] = profile_id

    update_user(user, profile_id)
    add_email_to_profile(profile_id, email, auth_code)

    return profile_id




def update_user(user, profile_id):

    auth_code = get_auth_code()

    for item, val in user.items():

        wrapped_user_part = {"extensions":{"hero":{"user": {item:val} }}}
        update_user_profile(wrapped_user_part, profile_id, auth_code)



def get_user_data(profile_id):

    try:
        auth_code = get_auth_code()
        ret_dict = get_user_profile(profile_id, auth_code)
        return ret_dict['extensions']['hero']['user']

    except:
        return {}



def get_user(email):

    user = create_plain_user()

    p_id = get_user_profile_id(email)
    if p_id is None:
        p_id = create_user(email)

    hy_user = get_user_data(p_id)

    user["star"].update(hy_user.get("star", {}))
    user["skills"].update(hy_user.get("skills", {}))

    hy_user["star"] = user["star"]
    hy_user["skills"] = user["skills"]

    user.update(hy_user)

    return user, p_id

