import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IJornadaInstructor, defaultValue } from 'app/shared/model/jornada-instructor.model';

export const ACTION_TYPES = {
  FETCH_JORNADAINSTRUCTOR_LIST: 'jornadaInstructor/FETCH_JORNADAINSTRUCTOR_LIST',
  FETCH_JORNADAINSTRUCTOR: 'jornadaInstructor/FETCH_JORNADAINSTRUCTOR',
  CREATE_JORNADAINSTRUCTOR: 'jornadaInstructor/CREATE_JORNADAINSTRUCTOR',
  UPDATE_JORNADAINSTRUCTOR: 'jornadaInstructor/UPDATE_JORNADAINSTRUCTOR',
  DELETE_JORNADAINSTRUCTOR: 'jornadaInstructor/DELETE_JORNADAINSTRUCTOR',
  RESET: 'jornadaInstructor/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IJornadaInstructor>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type JornadaInstructorState = Readonly<typeof initialState>;

// Reducer

export default (state: JornadaInstructorState = initialState, action): JornadaInstructorState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_JORNADAINSTRUCTOR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_JORNADAINSTRUCTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_JORNADAINSTRUCTOR):
    case REQUEST(ACTION_TYPES.UPDATE_JORNADAINSTRUCTOR):
    case REQUEST(ACTION_TYPES.DELETE_JORNADAINSTRUCTOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_JORNADAINSTRUCTOR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_JORNADAINSTRUCTOR):
    case FAILURE(ACTION_TYPES.CREATE_JORNADAINSTRUCTOR):
    case FAILURE(ACTION_TYPES.UPDATE_JORNADAINSTRUCTOR):
    case FAILURE(ACTION_TYPES.DELETE_JORNADAINSTRUCTOR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_JORNADAINSTRUCTOR_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_JORNADAINSTRUCTOR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_JORNADAINSTRUCTOR):
    case SUCCESS(ACTION_TYPES.UPDATE_JORNADAINSTRUCTOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_JORNADAINSTRUCTOR):
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

const apiUrl = 'api/jornada-instructors';

// Actions

export const getEntities: ICrudGetAllAction<IJornadaInstructor> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_JORNADAINSTRUCTOR_LIST,
    payload: axios.get<IJornadaInstructor>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IJornadaInstructor> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_JORNADAINSTRUCTOR,
    payload: axios.get<IJornadaInstructor>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IJornadaInstructor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_JORNADAINSTRUCTOR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IJornadaInstructor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_JORNADAINSTRUCTOR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IJornadaInstructor> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_JORNADAINSTRUCTOR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
