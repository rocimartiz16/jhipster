import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAreaInstructor, defaultValue } from 'app/shared/model/area-instructor.model';

export const ACTION_TYPES = {
  FETCH_AREAINSTRUCTOR_LIST: 'areaInstructor/FETCH_AREAINSTRUCTOR_LIST',
  FETCH_AREAINSTRUCTOR: 'areaInstructor/FETCH_AREAINSTRUCTOR',
  CREATE_AREAINSTRUCTOR: 'areaInstructor/CREATE_AREAINSTRUCTOR',
  UPDATE_AREAINSTRUCTOR: 'areaInstructor/UPDATE_AREAINSTRUCTOR',
  DELETE_AREAINSTRUCTOR: 'areaInstructor/DELETE_AREAINSTRUCTOR',
  RESET: 'areaInstructor/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAreaInstructor>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type AreaInstructorState = Readonly<typeof initialState>;

// Reducer

export default (state: AreaInstructorState = initialState, action): AreaInstructorState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_AREAINSTRUCTOR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_AREAINSTRUCTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_AREAINSTRUCTOR):
    case REQUEST(ACTION_TYPES.UPDATE_AREAINSTRUCTOR):
    case REQUEST(ACTION_TYPES.DELETE_AREAINSTRUCTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_AREAINSTRUCTOR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_AREAINSTRUCTOR):
    case FAILURE(ACTION_TYPES.CREATE_AREAINSTRUCTOR):
    case FAILURE(ACTION_TYPES.UPDATE_AREAINSTRUCTOR):
    case FAILURE(ACTION_TYPES.DELETE_AREAINSTRUCTOR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_AREAINSTRUCTOR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_AREAINSTRUCTOR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_AREAINSTRUCTOR):
    case SUCCESS(ACTION_TYPES.UPDATE_AREAINSTRUCTOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_AREAINSTRUCTOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/area-instructors';

// Actions

export const getEntities: ICrudGetAllAction<IAreaInstructor> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_AREAINSTRUCTOR_LIST,
  payload: axios.get<IAreaInstructor>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IAreaInstructor> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_AREAINSTRUCTOR,
    payload: axios.get<IAreaInstructor>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAreaInstructor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_AREAINSTRUCTOR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAreaInstructor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_AREAINSTRUCTOR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAreaInstructor> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_AREAINSTRUCTOR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
